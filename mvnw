#!/bin/sh
# Maven Wrapper Script

MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

# Read distributionUrl from properties
DISTRIBUTION_URL=$(grep "^distributionUrl=" "$MAVEN_WRAPPER_PROPERTIES" | sed 's/distributionUrl=//')

# Set MAVEN_USER_HOME
if [ -z "$MAVEN_USER_HOME" ]; then
  MAVEN_USER_HOME="$HOME/.m2/wrapper"
fi

# Derive local maven dir from distribution URL
DISTRIBUTION_ID=$(basename "$DISTRIBUTION_URL" .zip)
MAVEN_HOME="$MAVEN_USER_HOME/dists/$DISTRIBUTION_ID"
MVNEXEC="$MAVEN_HOME/bin/mvn"

# Download maven if not present
if [ ! -f "$MVNEXEC" ]; then
  echo "Downloading Maven from $DISTRIBUTION_URL ..."
  mkdir -p "$MAVEN_USER_HOME/dists"
  TMPFILE=$(mktemp /tmp/maven-XXXXXX.zip)
  curl -k -sL "$DISTRIBUTION_URL" -o "$TMPFILE"
  unzip -q "$TMPFILE" -d "$MAVEN_USER_HOME/dists/"
  rm -f "$TMPFILE"
  chmod +x "$MAVEN_HOME/bin/mvn"
fi

exec "$MVNEXEC" "$@"
