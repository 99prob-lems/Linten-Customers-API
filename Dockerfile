# Use the official Maven image with OpenJDK 20 as the base image
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container at /app
COPY . .

# Build the project in the container (this will also download all dependencies)
RUN mvn clean compile

# Now, use OpenJDK 20 for the runtime container
FROM maven:3.8.3-openjdk-17

# Set the working directory in the container
WORKDIR /app

# Copy the compiled code and the downloaded dependencies from the build container
COPY --from=build /app /app

# Run the Gatling tests when the container starts
CMD ["mvn", "gatling:test"]
