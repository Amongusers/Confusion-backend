//package com.example.amongserver.service.impl;
//
//import com.example.amongserver.domain.entity.User;
//import com.example.amongserver.dto.UserVoteDto;
//import com.example.amongserver.mapper.UserMapper;
//import com.example.amongserver.reposirory.UserRepository;
//import com.example.amongserver.service.UserVoteDtoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserVoteDtoServiceImpl implements UserVoteDtoService {
//    private final UserRepository userRepository;
//
//    @Override
//    public List<UserVoteDto> getAll() {
//        return userRepository.findAll()
//                .stream()
//                .map(UserMapper::toUserVoteDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public UserVoteDto getById(long id) {
//        Optional<User> user = userRepository.findById(id);
//        if(user.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");
//
//        return UserMapper.toUserVoteDto(user.get());
//    }
//
//    @Override
//    public UserVoteDto update(long id, UserVoteDto userVoteDto) {
//        Optional<User> userOptional = userRepository.findById(id);
//
//        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");
//
//        User user = userOptional.get();
//        if (userVoteDto.getNumberVotes() != null) user.setNumberVotes(userVoteDto.getNumberVotes());
//
//        return UserMapper.toUserVoteDto(userRepository.save(user));
//    }
//}
