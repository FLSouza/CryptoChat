#Gerando Keystore
sudo keytool -keystore certificate.jks -alias certificate -genkey -keyalg RSA

#Exportanto CRT
sudo keytool -export -alias certificate -keystore certificate.jks -file certificate.crt