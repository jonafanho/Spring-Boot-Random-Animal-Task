package org.animal.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service

import java.time.Duration
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ThreadLocalRandom

@Service
class AnimalService {

	private ScheduledFuture<?> repeatScheduledFuture
	private int previousIndex = -1

	private final SimpMessagingTemplate template
	private final ThreadPoolTaskScheduler scheduler

	private static final String[] WORDS = ["cat", "dog", "mouse", "horse", "fox"]
	private static final Duration REPEAT_DURATION = Duration.ofSeconds(5)

	AnimalService(SimpMessagingTemplate template) {
		this.template = template
		scheduler = new ThreadPoolTaskScheduler()
		scheduler.setPoolSize(1)
		scheduler.initialize()
	}

	/**
	 * Starts the service. Synchronized must be used to avoid race conditions.
	 */
	synchronized boolean start() {
		if (repeatScheduledFuture == null || repeatScheduledFuture.isCancelled()) {
			repeatScheduledFuture = scheduler.scheduleAtFixedRate({
				int index
				// Make sure the word is a new one
				do {
					index = ThreadLocalRandom.current().nextInt(WORDS.length)
				} while (index == previousIndex)

				previousIndex = index
				template.convertAndSend("/topic/animals", WORDS[index])
			}, REPEAT_DURATION)
			println("Animal service started")
		}

		return true
	}

	/**
	 * Not required, but helps with testing.
	 */
	synchronized boolean stop() {
		if (repeatScheduledFuture != null && !repeatScheduledFuture.isCancelled()) {
			repeatScheduledFuture.cancel(true)
			repeatScheduledFuture = null
			println("Animal service stopped")
		}

		return false
	}

	/**
	 * @return whether the service is running
	 */
	synchronized boolean status() {
		return repeatScheduledFuture != null
	}
}
