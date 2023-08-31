package io.tolgee.hateoas.activity

import io.tolgee.api.v2.controllers.ApiKeyController
import io.tolgee.model.views.activity.ProjectActivityView
import io.tolgee.service.AvatarService
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class ProjectActivityModelAssembler(
  private val avatarService: AvatarService
) : RepresentationModelAssemblerSupport<ProjectActivityView, ProjectActivityModel>(
  ApiKeyController::class.java, ProjectActivityModel::class.java
) {
  override fun toModel(view: ProjectActivityView): ProjectActivityModel {
    return ProjectActivityModel(
      revisionId = view.revisionId,
      timestamp = view.timestamp,
      type = view.type,
      author = view.authorId?.let {
        ProjectActivityAuthorModel(
          id = view.authorId!!,
          name = view.authorName,
          username = view.authorUsername!!,
          avatar = avatarService.getAvatarLinks(view.authorAvatarHash),
          deleted = view.authorDeleted
        )
      },
      modifiedEntities = getModifiedEntities(view),
      meta = view.meta,
      counts = view.counts,
      params = view.params
    )
  }

  private fun getModifiedEntities(view: ProjectActivityView) = view.modifications
    ?.groupBy { it.entityClass }
    ?.map { entry ->
      entry.key to entry.value.map {
        ModifiedEntityModel(
          entityId = it.entityId,
          description = it.description,
          modifications = it.modifications,
          relations = it.describingRelations,
          exists = it.exists
        )
      }
    }?.toMap()
}