package io.rsocket.examples.simpleservice;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.rpc.testing.protobuf.SimpleRequest;
import io.rsocket.rpc.testing.protobuf.SimpleResponse;
import io.rsocket.rpc.testing.protobuf.SimpleServiceClient;
import io.rsocket.transport.netty.client.TcpClientTransport;
import java.io.IOException;

class DefaultSimpleServiceClient {
  public static void main(String[] args) throws IOException {
    RSocket rSocket =
        RSocketFactory.connect().transport(TcpClientTransport.create(8081)).start().block();
    SimpleServiceClient serviceClient = new SimpleServiceClient(rSocket);
    serviceClient
        .fireAndForget(
            SimpleRequest.newBuilder()
                .setRequestMessage("fire and forget from rsocket client")
                .build())
        .block();
    SimpleResponse response =
        serviceClient
            .requestReply(
                SimpleRequest.newBuilder().setRequestMessage("request from rsocket client").build())
            .block();
    System.out.println("response -> " + response);
    serviceClient
        .requestStream(
            SimpleRequest.newBuilder()
                .setRequestMessage("stream request from rsocket client")
                .build())
        .subscribe(r -> System.out.println(r));
    System.in.read();
  }
}
