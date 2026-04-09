@org.springframework.modulith.ApplicationModule(
    displayName = "Ops",
    allowedDependencies = {
        "shared::api",
        "documents::api",
        "ingest::api",
        "indexing::api",
        "retrieval::api",
        "review::api",
        "knowledge::api"
    }
)
package org.cherrypick.picker.ops;
