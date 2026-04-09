@org.springframework.modulith.ApplicationModule(
    displayName = "Retrieval",
    allowedDependencies = {"shared::api", "documents::api", "indexing::api"}
)
package org.cherrypick.picker.retrieval;
