FROM sbtscala/scala-sbt:eclipse-temurin-jammy-22_36_1.10.0_3.4.2

# RUN apt update && \
#     apt install apt-transport-https curl gnupg -yqq && \
#     echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
#     echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list && \
#     curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | gpg --no-default-keyring --keyring gnupg-ring:/etc/apt/trusted.gpg.d/scalasbt-release.gpg --import && \
#     chmod 644 /etc/apt/trusted.gpg.d/scalasbt-release.gpg && \
#     apt update && \
#     apt install -y sbt

RUN apt update && \
    apt install -y \
    default-jdk \
    curl \
    dbus \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    xauth \
    xvfb \
    x11-apps \
    && rm -rf /var/lib/apt/lists/*

# Set environment variables for X11 forwarding
ENV DISPLAY=host.docker.internal:0
ENV LIBGL_ALWAYS_INDIRECT=true

# Create a directory for X11 UNIX socket
RUN mkdir -p /tmp/.X11-unix && chmod 1777 /tmp/.X11-unix

# Install JavaFX dependencies and set up environment
RUN apt update && \
    apt install -y \
    libgl1-mesa-glx \
    libgl1-mesa-dri \
    libgtk-3-0 \
    libxcomposite1 \
    libgconf-2-4 \
    libasound2 \
    libpulse-dev \
    libxtst6 \
    libxslt1.1 \
    libnss3 \
    libxss1 \
    libxkbcommon-x11-0 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN sbt assembly

CMD ["java", "-jar", "target/scala-3.3.3/Bohnanza.jar"]
