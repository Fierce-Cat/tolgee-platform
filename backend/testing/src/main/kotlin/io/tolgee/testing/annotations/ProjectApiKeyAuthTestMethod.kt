package io.tolgee.testing.annotations

import io.tolgee.model.enums.ApiScope
import org.junit.jupiter.api.Test

@Test
annotation class ProjectApiKeyAuthTestMethod(
  val apiKeyPresentType: ApiKeyPresentMode = ApiKeyPresentMode.HEADER,
  val scopes: Array<ApiScope> = [ApiScope.TRANSLATIONS_EDIT, ApiScope.KEYS_EDIT, ApiScope.TRANSLATIONS_VIEW]
)
