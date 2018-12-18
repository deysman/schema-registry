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
import org.apache.avro.SchemaValidationException;
import org.apache.avro.SchemaValidationStrategy;


/**
 * A {@link SchemaValidationStrategy} that checks that the {@link Schema} to
 * validate can read the existing schema according to the default Avro schema
 * resolution rules.
 *
 */
class CanReadValidationStrategy implements SchemaValidationStrategy {

  /**
   * Validate that the first schema provided can be used to read data written
   * with the second schema, according to the default Avro schema resolution
   * rules.
   *
   * @throws SchemaValidationException
   *           if the first schema cannot read data written by the second.
   */
  @Override
  public void validate(Schema toValidate, Schema existing)
      throws SchemaValidationException {
    MutualReadValidationStrategy.canRead(existing, toValidate);
  }

}
