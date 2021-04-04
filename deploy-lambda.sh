#!/bin/bash

FUNCTION_NAME=$1

chmod +x create_aws_config.sh
./create_aws_config.sh
PROJECT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
mvn -B -s settings.xml clean deploy -Dusername="$S3_ACCESS_KEY" -Dpassword="$S3_SECRET_ACCESS_KEY"
aws lambda update-function-code --function-name "$FUNCTION_NAME" --zip-file "fileb://target/lambda-$FUNCTION_NAME-${PROJECT_VERSION}-all.jar"
