package cloud.toolkit.oauth2.server

import java.util.UUID

import jobs.toolkit.service.CompositeService

object Oauth2Server extends CompositeService("oauth2" + UUID.randomUUID().toString){
	
}
