package com.example.catapi.data.api

import java.io.IOException

internal class NoConnectivityException : IOException("Sem conexão com a internet")
internal class ClientErrorException(message: String) : IOException(message)
internal class ServerException(message: String) : IOException(message)