/*********************************************************************************************************************
 *  Copyright 2020-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.                                      *
 *                                                                                                                    *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance    *
 *  with the License. A copy of the License is located at                                                             *
 *                                                                                                                    *
 *      http://www.apache.org/licenses/LICENSE-2.0                                                                    *
 *                                                                                                                    *
 *  or in the 'license' file accompanying this file. This file is distributed on an 'AS IS' BASIS, WITHOUT WARRANTIES *
 *  OR CONDITIONS OF ANY KIND, express or implied. See the License for the specific language governing permissions    *
 *  and limitations under the License.                                                                                *
 *********************************************************************************************************************/

import * as cdk from '@aws-cdk/core';
import { expect as expectCDK, haveResource, ResourcePart, SynthUtils } from '@aws-cdk/assert';

import { KafkaMetadata } from '../lib/msk-custom-resource';

test('creates custom resource for cluster metadata', () => {
    const app = new cdk.App();
    const stack = new cdk.Stack(app, 'TestStack');

    new KafkaMetadata(stack, 'Msk', {
        clusterArn: 'my-cluster-arn'
    });

    expect(SynthUtils.toCloudFormation(stack)).toMatchSnapshot();
    expectCDK(stack).to(haveResource('AWS::IAM::Role', {
        Metadata: {
            cfn_nag: {
                rules_to_suppress: [{
                    id: 'W11',
                    reason: 'MSK actions do not support resource level permissions'
                }]
            }
        }
    }, ResourcePart.CompleteDefinition));
});