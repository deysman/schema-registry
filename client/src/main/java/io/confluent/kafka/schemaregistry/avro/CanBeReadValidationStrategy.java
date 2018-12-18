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
 * A {@link SchemaValidationStrategy} that checks that the data written with the
 * {@link Schema} to validate can be read by the existing schema according to
 * the default Avro schema resolution rules.
 *
 */
class CanBeReadValidationStrategy implements SchemaValidationStrategy {

  /**
   * Validate that data written with first schema provided can be read using the
   * second schema, according to the default Avro schema resolution rules.
   *
   * @throws SchemaValidationException
   *           if the second schema cannot read data written by the first.
   */
  @Override
  public void validate(Schema toValidate, Schema existing)
      throws SchemaValidationException {
    MutualReadValidationStrategy.canRead(toValidate, existing);
  }

}
