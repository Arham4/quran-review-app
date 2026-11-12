FROM eclipse-temurin:17-jdk-jammy AS base

ENV DEBIAN_FRONTEND=noninteractive \
    ANDROID_HOME=/opt/android-sdk \
    ANDROID_SDK_ROOT=/opt/android-sdk \
    GRADLE_VERSION=8.10.2

ENV PATH=${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/build-tools/35.0.0:/opt/gradle/gradle-${GRADLE_VERSION}/bin:${PATH}

# System deps
RUN apt-get update && apt-get install -y --no-install-recommends \
    unzip curl zip ca-certificates git \
    && rm -rf /var/lib/apt/lists/*

# Install Gradle 8.10.2 manually so `gradle` exists
RUN curl -sSL https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o /tmp/gradle.zip \
    && mkdir -p /opt/gradle \
    && unzip -q /tmp/gradle.zip -d /opt/gradle \
    && rm /tmp/gradle.zip \
    && gradle --version

# Install Android SDK cmdline tools
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools \
    && curl -sSL https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -o /tmp/cmdtools.zip \
    && unzip -q /tmp/cmdtools.zip -d ${ANDROID_HOME}/cmdline-tools \
    && mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest \
    && rm /tmp/cmdtools.zip

# Accept licenses + install platform, build-tools, platform-tools
RUN yes | sdkmanager --licenses \
    && sdkmanager "platform-tools" "platforms;android-35" "build-tools;35.0.0"

WORKDIR /app

CMD ["gradle", "--version"]
