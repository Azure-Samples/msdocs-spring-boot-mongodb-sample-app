// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azure.appservice.examples.springbootmongodb.dao;

import com.microsoft.azure.appservice.examples.springbootmongodb.model.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends MongoRepository<TodoItem, String> {
}
