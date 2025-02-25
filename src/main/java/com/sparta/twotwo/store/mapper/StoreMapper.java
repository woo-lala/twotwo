package com.sparta.twotwo.store.mapper;

import com.sparta.twotwo.address.mapper.AddressMapper;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.store.dto.request.StoreCreateRequestDto;
import com.sparta.twotwo.store.dto.request.StoreUpdateRequestDto;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.repository.StoreCategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class StoreMapper {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected StoreCategoryRepository storeCategoryRepository;

    @Autowired
    protected AddressMapper addressMapper;


    @Mapping(target = "address", expression = "java(addressMapper.toAddress(requestDto.getAddress()))")
    @Mapping(target = "member", expression = "java(memberRepository.findById(requestDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException(\"Member not found\")))")
    @Mapping(target = "category", expression = "java(storeCategoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException(\"Category not found\")))")
    public abstract Store toStore(StoreCreateRequestDto requestDto);
    @Mapping(target = "address", expression = "java(requestDto.getAddress() != null ? addressMapper.toAddress(requestDto.getAddress()) : null)")
    @Mapping(target = "member", expression = "java(requestDto.getMemberId() != null ? memberRepository.findById(requestDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException(\"Member not found\")) : null)")
    @Mapping(target = "category", expression = "java(requestDto.getCategoryId() != null ? storeCategoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException(\"Category not found\")) : null)")
    public abstract Store toStore(StoreUpdateRequestDto requestDto);


}
