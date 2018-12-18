/**
 * Copyright 2014 Confluent Inc.
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

package io.confluent.kafka.schemaregistry.avro;


import org.apache.avro.Schema;
import org.apache.avro.SchemaCompatibility;
import org.apache.avro.SchemaCompatibility.SchemaCompatibilityType;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.SchemaValidationStrategy;
import org.apache.avro.AvroRuntimeException;

class MutualReadValidationStrategy implements SchemaValidationStrategy {

  /**
   * Validate that the schemas provided can mutually read data written by each
   * other according to the default Avro schema resolution rules.
   *
   * @throws SchemaValidationException if the schemas are not mutually compatible.
   */
  @Override
  public void validate(Schema toValidate, Schema existing)
      throws SchemaValidationException {
    canRead(toValidate, existing);
    canRead(existing, toValidate);
  }

  /**
   * Validates that data written with one schema can be read using another,
   * based on the default Avro schema resolution rules.
   *
   * @param writtenWith
   *          The "writer's" schema, representing data to be read.
   * @param readUsing
   *          The "reader's" schema, representing how the reader will interpret
   *          data.
   * @throws SchemaValidationException
   *           if the schema readUsing cannot be used to read data
   *           written with writtenWith
   */
  static void canRead(Schema writtenWith, Schema readUsing)
      throws SchemaValidationException {
    boolean error;
    try {
      SchemaCompatibilityType compatibility =
          SchemaCompatibility.checkReaderWriterCompatibility(readUsing, writtenWith).getType();

      error = (compatibility != SchemaCompatibilityType.COMPATIBLE);
    } catch (AvroRuntimeException e) {
      throw new SchemaValidationException(readUsing, writtenWith, e);
    }
    if (error) {
      throw new SchemaValidationException(readUsing, writtenWith);
    }
  }

}
