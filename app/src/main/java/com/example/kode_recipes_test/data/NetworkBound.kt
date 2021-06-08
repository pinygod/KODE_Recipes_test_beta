package com.example.kode_recipes_test.data

suspend fun <ResultType, RequestType> networkBound(
    query: suspend () -> ResultType,
    fetch: suspend () -> RequestType,
    saveFetchResult: suspend (RequestType) -> Unit,
    shouldFetch: () -> Boolean = { true }
) = if (shouldFetch()) {
    try {
        val fetched = fetch()
        saveFetchResult(fetched)
        query()
    } catch (throwable: Throwable) {
        query()
    }
} else {
    query()
}