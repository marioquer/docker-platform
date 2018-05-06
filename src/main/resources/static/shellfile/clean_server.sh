## clean docker
# kill running container
docker kill $(docker ps -a -q)
# remove stopped container
docker rm $(docker ps -a -q)
# remove images without dangling tag
docker rmi $(docker images -q -f dangling=true)
# remove all images
docker rmi $(docker images -q)


## uninstall docker
sudo apt-get purge -y docker-engine docker docker.io docker-ce
sudo apt-get autoremove -y --purge docker-engine docker docker.io docker-ce