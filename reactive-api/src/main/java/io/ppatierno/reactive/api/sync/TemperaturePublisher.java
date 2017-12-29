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

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class TemperaturePublisher implements Publisher<Integer> {

    private static final Logger log = LoggerFactory.getLogger(TemperaturePublisher.class);

    private Subscription subscription;

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        log.info("subscribe");

        this.subscription = new TemperatureSubscription(subscriber);

        subscriber.onSubscribe(this.subscription);
    }

    private class TemperatureSubscription implements Subscription {

        private final Logger log = LoggerFactory.getLogger(TemperatureSubscription.class);

        private final Subscriber<? super Integer> subscriber;

        private Random random = new Random();

        TemperatureSubscription(Subscriber<? super Integer> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long l) {
            log.info("request");

            int temperature = 0;

            for (int i = 0; i < l; i++) {
                temperature = random.nextInt(5) + 25;

                if (temperature < 30) {
                    this.subscriber.onNext(temperature);
                } else {
                    this.subscriber.onError(new Exception("temperature too high " + temperature));
                }
            }
        }

        @Override
        public void cancel() {
            log.info("cancel");
        }
    }
}
