/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4 foldmethod=marker */

/*
 * Copyright 2014 AT&T
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.att.api.dc.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * Immutable class that holds DC API response information.
 *
 * @author <a href="mailto:pk9069@att.com">Pavel Kazakov</a>
 * @version 3.0
 * @since 2.2
 */
public final class DCResponse {

    /** Contains a map of key-value pairs for device capabilities. */
    private Map<String, String> deviceMap;

    /**
     * Disallow default constructor call outside of class.
     *
     * @since 3.0
     */
    private DCResponse() {
    }

    /**
     * Create a DC response by parsing a JSON object.
     *
     * @param jobj JSON object to parse
     * @deprecated replaced by {@link #valueOf(JSONObject)}
     */
    @Deprecated
    public DCResponse(final JSONObject jobj) {
        DCResponse response = DCResponse.valueOf(jobj);
        this.deviceMap = response.deviceMap;
    }

    /**
     * Factory method for creating a DC response by parsing a JSON object.
     *
     * @param jobj JSON object to parse
     * @return DC response
     * @since 3.0
     */
    public static DCResponse valueOf(JSONObject jobj) {
        // TODO (pk9069): this class needs to be moved to an explicit model
        // and not just a map

        DCResponse response = new DCResponse();

        // use linkedhashmap to preserve order
        response.deviceMap = new LinkedHashMap<String, String>();

        JSONObject deviceInfo = jobj.getJSONObject("DeviceInfo");
        JSONObject deviceId = deviceInfo.getJSONObject("DeviceId");
        JSONObject capabilities = deviceInfo.getJSONObject("Capabilities");
        String typeAllocationCode = deviceId.getString("TypeAllocationCode");
        response.deviceMap.put("TypeAllocationCode", typeAllocationCode);

        // suppress generics warning
        @SuppressWarnings("unchecked")
        Iterator<String> keys = capabilities.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = capabilities.getString(key);
            response.deviceMap.put(key, value);
        }

        return response;
    }

    /**
     * Gets a map of key-value pairs for device capabilities.
     *
     * @return map of key-value pairs.
     */
    public Map<String, String> getResponseMap() {
        // TODO (pk9069): return copy, thereby avoid exposing internals
        return deviceMap;
    }
}
