# Spring_Boot_Master_Class
Spring Boot Master Class

Create a ConfigMap:
Define a ConfigMap using a YAML file. For example:
yaml
Copy code
apiVersion: v1
kind: ConfigMap
metadata:
  name: spring-app-config
data:
  MY_PROPERTY: value
  ANOTHER_PROPERTY: anotherValue
Apply this ConfigMap:
bash
Copy code
kubectl apply -f configmap.yml
Use ConfigMap in Deployment:
Modify your Deployment to include the ConfigMap values as environment variables for your application's container:
yaml
Copy code
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-app-deployment
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: spring-app
    spec:
      containers:
      - name: spring-app-container
        image: yourimage:latest
        env:
        - name: MY_PROPERTY
          valueFrom:
            configMapKeyRef:
              name: spring-app-config
              key: MY_PROPERTY
        - name: ANOTHER_PROPERTY
          valueFrom:
            configMapKeyRef:
              name: spring-app-config
              key: ANOTHER_PROPERTY
In the above deployment, we are fetching values from the ConfigMap and setting them as environment variables inside the Pod.
Reference Environment Variables in application.properties:
Spring Boot allows you to use environment variables directly in application.properties using the ${ENV_VARIABLE} syntax. You can reference the values set by the ConfigMap in application.properties:
properties
Copy code
my.property=${MY_PROPERTY}
another.property=${ANOTHER_PROPERTY}
If the environment variable isn't set, Spring Boot will throw an exception on startup. If you want to provide a default value, you can do it like this:
properties
Copy code
my.property=${MY_PROPERTY:defaultValue}
another.property=${ANOTHER_PROPERTY:anotherDefaultValue}
By following the above steps, your Spring Boot application will now pick up values from the Kubernetes ConfigMap and use them in its properties. Remember, if you have sensitive information, consider Kubernetes Secrets and handle them with extra care in your applications.
