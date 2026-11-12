#!/bin/sh
# Minimal gradlew shim: delegates to system `gradle` (installed in Docker image).
# Falls back to downloading the distribution if `gradle` is unavailable.
set -e
if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
else
  echo "gradle binary not found, please install Gradle 8.10.2" >&2
  exit 1
fi
