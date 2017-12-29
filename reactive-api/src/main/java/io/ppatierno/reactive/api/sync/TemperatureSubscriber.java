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

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperatureSubscriber implements Subscriber<Integer> {

    private static final Logger log = LoggerFactory.getLogger(TemperatureSubscriber.class);

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("onSubscribe");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer integer) {
        log.info("onNext");
        log.info("value = {}", integer);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("onError");
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }
}
