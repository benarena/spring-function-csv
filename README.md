# Spring Cloud Function Lambda
This service will ingest a CSV file from an S3 bucket and place
the results on a Kinesis topic. It is triggered each time a file 
is added to the bucket. 

# Build
The service can be built using maven: `mvn install`

# Deploy
The service is deployed using the [Serverless Framework](http://serverless.com):
`sls deploy`.

# Lessons Learned
[Spring Cloud Function documentation](https://cloud.spring.io/spring-cloud-function/reference/html/) 
is still lacking in ease of understanding. There are also very 
few results on Stack Overflow and Google Search for this library.

Performance of the lambda itself is poor. Cold start run times are
around 17-20 seconds. Warm run times average 700ms for a 5 line CSV
file. The same functionality in a NodeJS lambda has a cold run
time <300ms and a warm run time of ~75ms.
