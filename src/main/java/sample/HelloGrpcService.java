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

import java.util.Random;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.grpc.HelloReply;
import sample.grpc.HelloRequest;
import sample.grpc.HelloServiceGrpc.HelloServiceImplBase;

/**
 * Implementation of the server-side grpc HelloService.
 *
 * NOTE: GrpcService is a composite annotation that extends Service, so this class will be
 * picked up in a component scan.
 *
 * @author tyler.vangorder
 *
 */
@GRpcService
public class HelloGrpcService extends HelloServiceImplBase {

	private Logger logger = LoggerFactory.getLogger(HelloGrpcService.class);

	private final Random random = new Random();

	@Override
	public void sayHello(HelloRequest request,
			StreamObserver<HelloReply> responseObserver) {
		String message = "Hello " + request.getName();
		try {
			Thread.sleep(this.random.nextInt(1000));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.logger.debug("In the grpc server stub.");
		HelloReply reply = HelloReply.newBuilder().setMessage(message).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

}
