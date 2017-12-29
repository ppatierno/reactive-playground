/**
 * Copyright 2017 Paolo Patierno
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.ù
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.ppatierno.reactive.api.sync;

import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        new Application().run();
        System.in.read();
    }

    public void run() {

        Publisher<Integer> publisher = new TemperaturePublisher();
        Subscriber<Integer> subscriber = new TemperatureSubscriber();

        publisher.subscribe(subscriber);
    }

    public void runWithProcessor() {

        Publisher<Integer> publisher = new TemperaturePublisher();
        Processor<Integer, Integer> processor = new TemperatureProcessor(i -> i + 10);
        Subscriber<Integer> subscriber = new TemperatureSubscriber();

        publisher.subscribe(processor);
        processor.subscribe(subscriber);
    }
}
