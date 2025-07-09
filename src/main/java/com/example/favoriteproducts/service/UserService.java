package com.example.favoriteproducts.service;

import com.example.favoriteproducts.dto.UserDTO;
import com.example.favoriteproducts.entity.User;
import com.example.favoriteproducts.mapper.UserMapper;
import com.example.favoriteproducts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO);
    }

    public UserDTO create(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso: " + userDTO.getEmail());
        }
        
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));

        // Verificar se o email já está em uso por outro usuário
        if (!existingUser.getEmail().equals(userDTO.getEmail()) && 
            userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso: " + userDTO.getEmail());
        }

        userMapper.updateEntityFromDTO(userDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
        
        // Verificar se o usuário possui produtos antes de deletar
        if (userRepository.findById(id).get().getProducts().size() > 0) {
            throw new IllegalArgumentException("Não é possível deletar usuário que possui produtos associados. ID: " + id);
        }
        
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
} 