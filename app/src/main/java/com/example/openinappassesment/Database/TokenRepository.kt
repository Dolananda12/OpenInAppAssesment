package com.example.openinappassesment.Database

class TokenRepository(private val tokenDAO: TokenDAO){
  val tokens = tokenDAO.getToken()
  suspend fun insertToken(token: Token){
      tokenDAO.insertToken(token)
  }
  suspend fun updateToken(token: Token){
      tokenDAO.updateToken(token)
  }
  suspend fun deleteToken(token: Token){
      tokenDAO.deleteToken(token)
  }
}