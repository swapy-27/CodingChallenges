Description

This project demonstrates Three popular rate limiting algorithms in JAVA: the token bucket, fixed window and sliding window counter. It provides practical examples through HTTP handlers, allowing you to explore their behavior and configure them for different rate limiting needs.

Key Features

Three Algorithms: Choose between the flexible token bucket approach with gradual refills or the fixed window counter for burst protection within a specific timeframe, or Sliding Window.
IP-Based Tracking: Monitor and limit requests based on client IP addresses.
Thread-Safe Design: Utilizes mutexes for concurrent access to internal data structures, ensuring safe operation in multi-threaded environments.
Configurability: Customize rate limits through adjustable constants for tokens, refill rate, window size, and maximum requests.