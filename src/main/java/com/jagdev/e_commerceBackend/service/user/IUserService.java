package com.jagdev.e_commerceBackend.service.user;

import com.jagdev.e_commerceBackend.model.User;
import com.jagdev.e_commerceBackend.request_dto.CreateUserRequest;
import com.jagdev.e_commerceBackend.request_dto.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
}
