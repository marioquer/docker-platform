## ubuntu

# sudo issue
# HOSTS_FILE_URL="/etc/hosts"
# HOST_NAME=$(hostname)

# localhostIp=$(cat $HOSTS_FILE_URL | grep "127.0.1.1")

# if [ $localhostIp = "" ];then


## install docker
sudo apt-get update
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
sudo apt-get update
echo y | sudo apt-get install docker-ce