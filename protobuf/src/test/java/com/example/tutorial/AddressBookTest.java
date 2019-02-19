package com.example.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.tutorial.AddressBookProtos.Person;
import com.google.protobuf.Timestamp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AddressBookTest {
  private final Logger logger = LoggerFactory.getLogger(AddressBookTest.class);

  @Test
  public void verifyObjectEquality() throws IOException {
    var lhs = person();

    logger.info("{}", lhs);

    var output = new ByteArrayOutputStream();

    lhs.writeTo(output);

    var input = new ByteArrayInputStream(output.toByteArray());

    assertEquals(lhs, Person.parseFrom(input));
  }

  private Person person() {
    return Person.newBuilder()
        .setName("Mickey Mouse")
        .setId(123)
        .setEmail("Mickey.Mouse@disney.com")
        .setLastUpdated(timestamp())
        .addPhones(phoneNumber())
        .build();
  }

  private Person.PhoneNumber phoneNumber() {
    return Person.PhoneNumber.newBuilder()
        .setNumber("(818) 555-1212")
        .setType(Person.PhoneType.MOBILE)
        .build();
  }

  private Timestamp timestamp() {
    var instant = Instant.now();

    return Timestamp.newBuilder()
        .setSeconds(instant.getEpochSecond())
        .setNanos(instant.getNano())
        .build();
  }
}
