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
    serviceClient.fireAndForget(request("fire and forget from rsocket client")).block();
    SimpleResponse response =
        serviceClient.requestReply(request("request from rsocket client")).block();
    System.out.println("response -> " + response);
    serviceClient
        .requestStream(request("stream request from rsocket client"))
        .subscribe(r -> System.out.println(r));
    System.in.read();
  }

  private static SimpleRequest request(String msg) {
    return SimpleRequest.newBuilder().setRequestMessage(msg).build();
  }
}
