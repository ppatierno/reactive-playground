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
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class TemperatureProcessor implements Processor<Integer, Integer> {

    private static final Logger log = LoggerFactory.getLogger(TemperatureProcessor.class);

    private Function function;

    private Subscription subscription;
    private Subscription publisherSubscription;

    private int newValue;

    public TemperatureProcessor(Function<Integer, Integer> function) {
        this.function = function;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        log.info("subscribe");

        this.subscription = new ProcessorSubscription(subscriber);
        subscriber.onSubscribe(this.subscription);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("onSubscribe");

        this.publisherSubscription = subscription;
    }

    @Override
    public void onNext(Integer integer) {
        log.info("onNext");

        this.newValue = (int) function.apply(integer);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("onError");
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }


    private class ProcessorSubscription implements Subscription {

        private final Logger log = LoggerFactory.getLogger(ProcessorSubscription.class);

        private final Subscriber<? super Integer> subscriber;

        ProcessorSubscription(Subscriber<? super Integer> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long l) {
            log.info("request");

            publisherSubscription.request(1);
            this.subscriber.onNext(newValue);
        }

        @Override
        public void cancel() {
            log.info("cancel");
        }
    }
}
