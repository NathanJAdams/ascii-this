#!/bin/bash

mkdir ~/.aws
touch ~/.aws/config
echo "[default]" >> ~/.aws/config
echo "aws_access_key_id=${S3_ACCESS_KEY}" >> ~/.aws/config
echo "aws_secret_access_key=${S3_SECRET_ACCESS_KEY}" >> ~/.aws/config
echo "region=eu-west-1" >> ~/.aws/config
echo "output=json" >> ~/.aws/config
