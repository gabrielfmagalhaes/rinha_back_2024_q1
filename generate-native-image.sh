# Build Image 
./mvnw package -Dnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true

# Create Docker Image
docker build -f src/main/docker/Dockerfile.native-micro -t k4gero/quarkus-rinha-back-2024-q1:XXXX .

# Create Tag
docker tag k4gero/quarkus-rinha-back-2024-q1:XXXX k4gero/quarkus-rinha-back-2024-q1:latest

# Push
docker push k4gero/quarkus-rinha-back-2024-q1:XXXX
docker push k4gero/quarkus-rinha-back-2024-q1:latest
