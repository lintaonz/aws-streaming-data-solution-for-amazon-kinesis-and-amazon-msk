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

package com.demo.events;

/**
 * 
 * { "track_id": 36, "spider": "JE8LDRJSJT", "power_cycle": 36, "avg_pitch":
 * -0.034775496, "avg_roll": 0.008343337, "avg_yaw": 0.32535574, "avg_v_speed":
 * 0.0070177596, "avg_altitude": 765.29144, "avg_lati": 30.505756, "avg_long":
 * -95.14779, "avg_vn": 0.1617198057, "avg_ve": -0.2067287654, "avg_vd":
 * -0.0070177596, "avg_gspped": 40.792923471, "avg_h": 0.3073974703 }
 */
public class RideRequest extends Event {
    // Trip ID is included to support deprecated event schema.
    @SuppressWarnings("squid:ClassVariableVisibilityCheck")
    public Long trackId;

    public final String spider;
    public final Long powerCycle;
    public final Double avgPitch;
    public final Double avgRoll;
    public final Double avgYaw;
    public final Double avgVSpeed;
    public final Double avgAltitude;
    public final Double avgLati;
    public final Double avgLong;
    public final Double avgVn;
    public final Double avgVe;
    public final Double avgVd;
    public final Double avgGspped;
    public final Double avgH;
    public final String profile;

    public RideRequest(Long powerCycle, String spider, Double avgPitch, Double avgRoll, Double avgYaw, Double avgVSpeed,
            Double avgAltitude, Double avgLati, Double avgLong, Double avgVn, Double avgVe, Double avgVd,
            Double avgGspped, Double avgH) {
        this.powerCycle = powerCycle;
        this.spider = spider;
        this.avgPitch = avgPitch;
        this.avgYaw = avgYaw;
        this.avgRoll = avgRoll;
        this.avgVSpeed = avgVSpeed;
        this.avgAltitude = avgAltitude;
        this.avgLati = avgLati;
        this.avgLong = avgLong;
        this.avgVn = avgVn;
        this.avgVe = avgVe;
        this.avgVd = avgVd;
        this.avgGspped = avgGspped;
        this.avgH = avgH;
        this.profile = null;
    }

    public RideRequest(Long powerCycle, String spider, Double avgPitch, Double avgRoll, Double avgYaw, Double avgVSpeed,
            Double avgAltitude, Double avgLati, Double avgLong, Double avgVn, Double avgVe, Double avgVd,
            Double avgGspped, Double avgH, String profile) {
        this.powerCycle = powerCycle;
        this.spider = spider;
        this.avgPitch = avgPitch;
        this.avgYaw = avgYaw;
        this.avgRoll = avgRoll;
        this.avgVSpeed = avgVSpeed;
        this.avgAltitude = avgAltitude;
        this.avgLati = avgLati;
        this.avgLong = avgLong;
        this.avgVn = avgVn;
        this.avgVe = avgVe;
        this.avgVd = avgVd;
        this.avgGspped = avgGspped;
        this.avgH = avgH;
        this.profile = profile;
    }    

    public RideRequest withProfile(String profile) {
        return new RideRequest(this.powerCycle, this.spider, this.avgPitch, this.avgRoll, this.avgYaw, this.avgVSpeed,
                this.avgAltitude, this.avgLati, this.avgLong, this.avgVn, this.avgVe, this.avgVd, this.avgGspped,
                this.avgH, profile);
    }
}
