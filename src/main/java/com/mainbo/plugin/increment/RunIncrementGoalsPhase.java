package com.mainbo.plugin.increment;

import java.io.File;
import java.util.List;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.apache.maven.shared.release.phase.AbstractRunGoalsPhase;

/**
 * Run the integration tests for the project to verify that it builds before
 * committing.
 *
 */
public class RunIncrementGoalsPhase extends AbstractRunGoalsPhase {
  @Override
  public ReleaseResult execute(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException {
    return execute(releaseDescriptor, releaseEnvironment, new File(releaseDescriptor.getWorkingDirectory()),
        releaseDescriptor.getAdditionalArguments());
  }

  @Override
  public ReleaseResult simulate(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException {
    ReleaseResult result = new ReleaseResult();

    logInfo(result, "Executing increment goals - since this is simulation mode it is running against the "
        + "original project, not the rewritten ones");

    execute(releaseDescriptor, releaseEnvironment, reactorProjects);

    return result;
  }

  @Override
  protected String getGoals(ReleaseDescriptor releaseDescriptor) {
    return ((MergeReleaseDescriptor) releaseDescriptor).getIncrementGoals();
  }
}
