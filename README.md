# AWS Streaming Data Solution for Amazon Kinesis and AWS Streaming Data Solution for Amazon MSK
Streaming data use cases follow a similar pattern where data flows from data producers through streaming storage and data consumers to storage destinations. Sources continuously generate data, which is delivered via the ingest stage to the stream storage layer, where it's durably captured and made available for streaming processing. The stream processing layer processes the data in the stream storage layer and sends the processed information to a specified destination.

The challenge with these use cases is the set up time and effort that developers require to create the resources and establish the best practices needed by the streaming data services (such as access control, logging capabilities, and data integrations).

The [AWS Streaming Data Solution for Amazon Kinesis](https://aws.amazon.com/solutions/implementations/aws-streaming-data-solution-for-amazon-kinesis) and [AWS Streaming Data Solution for Amazon MSK](https://aws.amazon.com/solutions/implementations/aws-streaming-data-solution-for-amazon-msk) automatically configure the AWS services necessary to easily capture, store, process, and deliver streaming data. They provide common streaming data patterns for you to choose from that can serve as a starting point for solving your use case or to improve existing applications. You can try out new service combinations to implement common streaming data use cases, or use the solutions as the basis for your production environment.

## Table of contents
- [Architecture for AWS Streaming Data Solution for Amazon Kinesis](source/docs/README-Kinesis.md)
- [Architecture for AWS Streaming Data Solution for Amazon MSK](source/docs/README-MSK.md)
- [AWS CDK Constructs](#aws-cdk-constructs)
- [Project structure](#project-structure)
- [Deployment](#deployment)
- [Creating a custom build](#creating-a-custom-build)
- [Known issues](#known-issues)
- [Additional Resources](#additional-resources)

## AWS CDK Constructs
[AWS CDK Solutions Constructs](https://aws.amazon.com/solutions/constructs/) make it easier to consistently create well-architected applications. All AWS Solutions Constructs are reviewed by AWS and use best practices established by the AWS Well-Architected Framework. This solution uses the following AWS CDK Constructs:
- aws-apigateway-kinesisstreams
- aws-apigateway-lambda
- aws-kinesisfirehose-s3
- aws-kinesisstreams-lambda

## Project structure
```
├── deployment
│   └── cdk-solution-helper  [Lightweight helper that cleans-up synthesized templates from the CDK]
├── source
│   ├── bin                  [Entrypoint of the CDK application]
│   ├── docs                 [Architecture diagrams for each solution]
│   ├── kinesis              [Demo applications for the KPL and Apache Flink]
│   ├── lambda               [Custom resources for features not supported by CloudFormation]
│   ├── lib                  [Constructs for the components of the solution]
│   ├── patterns             [Stack definitions]
│   └── test                 [Unit tests]
```

## Deployment
You can launch this solution with one click from the solution home pages:
- [AWS Streaming Data Solution for Amazon Kinesis](https://aws.amazon.com/solutions/implementations/aws-streaming-data-solution-for-amazon-kinesis)
- [AWS Streaming Data Solution for Amazon MSK](https://aws.amazon.com/solutions/implementations/aws-streaming-data-solution-for-amazon-msk)

> **Please ensure you test the templates before updating any production deployments.**

## Creating a custom build
To customize the solution, follow the steps below:

### Prerequisites
* [AWS Command Line Interface](https://aws.amazon.com/cli/)
* Node.js 12.x or later
* Python 3.8 or later
* Java 11 (only required if using Apache Flink)
* Apache Maven 3.1 (only required if using Apache Flink)

> **Note**: The commands listed below will build all patterns. To only include one, you can modify the CDK entrypoint file on `source/bin/streaming-data-solution.ts`

### 1. Download or clone this repo
```
git clone https://github.com/awslabs/aws-streaming-data-solution-for-amazon-kinesis-and-amazon-msk
```

### 2. After introducing changes, run the unit tests to make sure the customizations don't break existing functionality
```
cd ./source
chmod +x ./run-all-tests.sh
./run-all-tests.sh
```

### 3. Build the solution for deployment
> **Note**: In order to compile the solution, the _build-s3_ will install the AWS CDK.

```
ARTIFACT_BUCKET=my-bucket-name     # S3 bucket name where customized code will reside
SOLUTION_NAME=my-solution-name     # customized solution name
VERSION=my-version                 # version number for the customized code

cd ./deployment
chmod +x ./build-s3-dist.sh
./build-s3-dist.sh $ARTIFACT_BUCKET $SOLUTION_NAME $VERSION
```

```shell
export ARTIFACT_BUCKET=streaming-data-solution
export SOLUTION_NAME=streaming-data
export VERSION=v1.0.0

export ARTIFACT_BUCKET=temp-test-us-west-2-spidertracks-build-artifacts
export SOLUTION_NAME=data-hackathon-ml-flight-events
export VERSION=v0.0.1

brew install wget
```

> **Why doesn't the solution use CDK deploy?** This solution includes a few Lambda functions, and by default CDK deploy will not install any dependencies (it'll only zip the contents of the path specified in _fromAsset_). In future releases, we'll look into leveraging bundling assets using [Docker](https://docs.aws.amazon.com/cdk/api/latest/docs/aws-lambda-readme.html#bundling-asset-code).

> In addition to that, there are also some extra components (such as the demo applications for the KPL and Kinesis Data Analytics) that are implemented in Java, and the _build-s3_ script takes care of packaging them.

### 4. Upload deployment assets to your Amazon S3 buckets
Create the CloudFormation bucket defined above, as well as a regional bucket in the region you wish to deploy. The CloudFormation templates are configured to pull the Lambda deployment packages from Amazon S3 bucket in the region the template is being launched in.

```
aws s3 mb s3://$ARTIFACT_BUCKET --region us-east-1
aws s3 mb s3://$ARTIFACT_BUCKET-us-east-1 --region us-east-1
```

```
aws s3 sync ./global-s3-assets s3://$ARTIFACT_BUCKET/$SOLUTION_NAME/$VERSION --acl bucket-owner-full-control
aws s3 sync ./regional-s3-assets s3://$ARTIFACT_BUCKET-us-east-1/$SOLUTION_NAME/$VERSION --acl bucket-owner-full-control
```

### 5. Launch the CloudFormation template
* Get the link of the template uploaded to your Amazon S3 bucket (created as $ARTIFACT_BUCKET in the previous step)
* Deploy the solution to your account by launching a new AWS CloudFormation stack

## Known issues
* For the options that use Amazon Kinesis Data Analytics, we recommend stopping the application before you delete the stack.
If the application is running during the stack deletion, its status will change to `Updating`, and you might see some errors when CloudFormation tries to delete resources such as `AWS::KinesisAnalyticsV2::ApplicationCloudWatchLoggingOption` and `Custom::VpcConfiguration` (a custom resource that configures the application to connect to a virtual private cloud).

## Additional Resources

### Services
- [Amazon Kinesis Data Streams](https://aws.amazon.com/kinesis/data-streams/)
- [Amazon Kinesis Data Firehose](https://aws.amazon.com/kinesis/data-firehose/)
- [Amazon Kinesis Data Analytics](https://aws.amazon.com/kinesis/data-analytics/)
- [Amazon Managed Streaming for Apache Kafka (Amazon MSK)](https://aws.amazon.com/msk/)
- [AWS Lambda](https://aws.amazon.com/lambda/)

### Other
- [Kinesis Producer Library](https://github.com/awslabs/amazon-kinesis-producer)
- [Amazon Kinesis Replay](https://github.com/aws-samples/amazon-kinesis-replay)
- [Amazon Kinesis Data Analytics Java Examples](https://github.com/aws-samples/amazon-kinesis-data-analytics-java-examples)
- [Flink: Hands-on Training](https://ci.apache.org/projects/flink/flink-docs-master/learn-flink/)
- [Streaming Analytics Workshop](https://streaming-analytics.workshop.aws/flink-on-kda.html)
- [Kinesis Scaling Utility](https://github.com/awslabs/amazon-kinesis-scaling-utils)
- [Amazon MSK Labs](https://amazonmsk-labs.workshop.aws/en)
- [Using Amazon MSK as an event source for AWS Lambda](https://aws.amazon.com/blogs/compute/using-amazon-msk-as-an-event-source-for-aws-lambda/)

***

Copyright 2020-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
