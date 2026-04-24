#!/usr/bin/env python3
"""Check whether new annual programming-language ranking surveys have been
published. If a new one is detected, open a GitHub issue (unless one already
exists for that source)."""

import re
import subprocess
import sys
import urllib.request
import urllib.error

# Each entry: (display name, current latest year, list of URL patterns to probe)
# A pattern is either a plain URL (checked for HTTP 200) or a tuple
# ("search", url, regex) meaning "fetch the page and look for the regex".
SOURCES = [
    (
        "Stack Overflow Developer Survey",
        2024,
        ["https://survey.stackoverflow.co/{year}/technology/"],
    ),
    (
        "IEEE Spectrum Top Programming Languages",
        2025,
        ["https://spectrum.ieee.org/top-programming-languages-{year}"],
    ),
    (
        "JetBrains Developer Ecosystem Survey",
        2024,
        ["https://www.jetbrains.com/lp/devecosystem-{year}/"],
    ),
    (
        "GitHub Octoverse",
        2025,
        [("search", "https://github.blog/news-insights/octoverse/", r"octoverse.*{year}")],
    ),
    (
        "RedMonk Programming Language Rankings",
        2025,
        [("search", "https://redmonk.com/sogrady/", r"sogrady/{year}/\d+/\d+/language-rankings")],
    ),
]

YEARS_AHEAD = 2


def url_exists(url: str) -> bool:
    req = urllib.request.Request(url, method="HEAD")
    req.add_header("User-Agent", "PLR-Rankings-Checker/1.0")
    try:
        with urllib.request.urlopen(req, timeout=15) as resp:
            return resp.status < 400
    except (urllib.error.HTTPError, urllib.error.URLError, OSError):
        pass
    req = urllib.request.Request(url, method="GET")
    req.add_header("User-Agent", "PLR-Rankings-Checker/1.0")
    try:
        with urllib.request.urlopen(req, timeout=15) as resp:
            return resp.status < 400
    except (urllib.error.HTTPError, urllib.error.URLError, OSError):
        return False


def page_matches(url: str, pattern: str) -> bool:
    req = urllib.request.Request(url)
    req.add_header("User-Agent", "PLR-Rankings-Checker/1.0")
    try:
        with urllib.request.urlopen(req, timeout=15) as resp:
            body = resp.read().decode("utf-8", errors="replace")
            return bool(re.search(pattern, body))
    except (urllib.error.HTTPError, urllib.error.URLError, OSError):
        return False


def open_issue_exists(source_name: str) -> bool:
    result = subprocess.run(
        ["gh", "issue", "list", "--state", "open", "--search", source_name, "--json", "title"],
        capture_output=True, text=True,
    )
    if result.returncode != 0:
        return False
    return source_name.lower() in result.stdout.lower()


def create_issue(source_name: str, year: int, url: str) -> None:
    title = f"Update {source_name} ({year})"
    body = (
        f"A new **{source_name}** for **{year}** appears to be available.\n\n"
        f"URL: {url}\n\n"
        f"Please update the ranking data."
    )
    subprocess.run(
        ["gh", "issue", "create", "--title", title, "--body", body],
        check=True,
    )
    print(f"  -> Issue created: {title}")


def check_source(name: str, current_year: int, patterns: list) -> None:
    print(f"Checking {name} (current: {current_year}) ...")
    for year in range(current_year + 1, current_year + 1 + YEARS_AHEAD):
        for pat in patterns:
            found_url = None
            if isinstance(pat, tuple):
                _, url, regex = pat
                regex_resolved = regex.replace("{year}", str(year))
                if page_matches(url, regex_resolved):
                    found_url = url
            else:
                url = pat.replace("{year}", str(year))
                if url_exists(url):
                    found_url = url

            if found_url:
                print(f"  Found new ranking: {name} {year}")
                if open_issue_exists(name):
                    print(f"  Open issue already exists, skipping.")
                else:
                    create_issue(name, year, found_url)
                return
    print(f"  No new ranking found.")


def main() -> None:
    for name, current_year, patterns in SOURCES:
        check_source(name, current_year, patterns)


if __name__ == "__main__":
    main()
