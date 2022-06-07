/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.server;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Path("/v1/graphviz")
public class OptimizerStepResource
{
    @Inject
    public OptimizerStepResource() {}

    @GET
    @javax.ws.rs.Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptimizerSteps()
    {
        try {
            List<String> fileList = new ArrayList<>();
            java.nio.file.Path path = Paths.get("/tmp/plan");
            if (Files.exists(path)) {
                Files.list(path).forEach(file -> {
                    fileList.add(file.getFileName().toString());
                });
            }
            Collections.sort(fileList);

            return Response.ok(fileList).build();
        }
        catch (NoSuchElementException | IOException e) {
            return Response.status(Response.Status.GONE).build();
        }
    }

    @GET
    @javax.ws.rs.Path("{file}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStepPlan(@PathParam("file") String file)
    {
        try {
            java.nio.file.Path path = Paths.get("/tmp/plan/" + file);
            Map<String, String> data = new HashMap<>();
            if (Files.exists(path)) {
                data.put("data", new String(Files.readAllBytes(path)));
            }
            return Response.ok(data).build();
        }
        catch (NoSuchElementException | IOException e) {
            return Response.status(Response.Status.GONE).build();
        }
    }
}
