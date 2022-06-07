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
package com.facebook.presto.sql.planner.planPrinter;

import com.facebook.presto.Session;
import com.facebook.presto.metadata.FunctionAndTypeManager;
import com.facebook.presto.spi.plan.PlanNode;
import com.facebook.presto.sql.planner.TypeProvider;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OptimizerUtils
{
    public static void printGraphVizToTmpPlanDir(String path, int index, String ruleName, PlanNode plan, TypeProvider types, Session session, FunctionAndTypeManager functionAndTypeManager)
    {
        PrintWriter out;
        try {
            out = new PrintWriter(path + String.format("%05d", index) + "-" + ruleName + ".dot");
            out.println(PlanPrinter.graphvizLogicalPlan(plan, types, session, functionAndTypeManager));
            out.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
