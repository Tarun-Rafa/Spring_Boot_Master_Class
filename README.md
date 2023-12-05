# Spring_Boot_Master_Class
Spring Boot Master Class

apiVersion: apps/v1
kind: Deployment
metadata:
  name: myservice
spec:
  template:
    spec:
      containers:
      - name: myservice-container
        image: myservice-image
        volumeMounts:
        - mountPath: /etc/tls
          name: tls-volume
      volumes:
      - name: tls-volume
        secret:
          secretName: myservice-tls
