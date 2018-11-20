/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.grpc.HelloReply;
import sample.grpc.HelloRequest;
import sample.grpc.HelloServiceGrpc;
import sample.grpc.HelloServiceGrpc.HelloServiceFutureStub;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.instrument.grpc.GrpcClientManagedChannelBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * The client uses the GrpcClientManagedChannelBuilder to create the managed channel. This
 * builder is Spring-aware and will inject any global, client-side interceptors into
 * channels created with the builder.
 *
 * @author tyler.vangorder
 */
@Component
@EnableConfigurationProperties(GrpcClientProperties.class)
public class HelloServiceGrpcClient implements HelloServiceClient {

	private Logger logger = LoggerFactory.getLogger(HelloServiceGrpcClient.class);

	private GrpcClientProperties clientProperties;

	private GrpcClientManagedChannelBuilder managedChannelBuilder;

	public HelloServiceGrpcClient(GrpcClientProperties clientProperties,
			GrpcClientManagedChannelBuilder managedChannelBuilder) {
		this.clientProperties = clientProperties;
		this.managedChannelBuilder = managedChannelBuilder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sample.HelloServiceClient#sayHello(java.lang.String)
	 */
	@Override
	public String sayHello(String name) throws Exception {

		this.logger.debug("In grpc client.");
		HelloServiceFutureStub futureStub = HelloServiceGrpc
				.newFutureStub(getManagedChannel());

		ListenableFuture<HelloReply> future = futureStub
				.sayHello(HelloRequest.newBuilder().setName(name).build());

		HelloReply reply = future.get(2000, TimeUnit.MILLISECONDS);
		return reply.getMessage();

	}

	private ManagedChannel getManagedChannel() {
		return this.managedChannelBuilder.forAddress(this.clientProperties.getHost(),
				this.clientProperties.getPort()).usePlaintext().build();
	}

}
