package com.sparta.twotwo.order.entity;

import com.sparta.twotwo.enums.OrderType;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import com.sparta.twotwo.order.repository.OrderRepository;
import com.sparta.twotwo.address.entity.Address;
import com.sparta.twotwo.address.entity.Area;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.entity.StoreCategory;
import com.sparta.twotwo.address.repository.AddressRepository;
import com.sparta.twotwo.address.repository.AreaRepository;
import com.sparta.twotwo.store.repository.StoreCategoryRepository;
import com.sparta.twotwo.store.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class OrderTest {


    @Autowired
    private OrderRepository repository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StoreCategoryRepository storeCategoryRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("스토어 생성")
//    @Transactional
    public void saveStore() {


        StoreCategory category = new StoreCategory("카테고리");
        storeCategoryRepository.save(category);

        for (int i = 10; i < 20; i++) {
            Area are = new Area(i + "시", i + "구", i + "동", i + "", i + "");
            areaRepository.save(are);

            Address address = new Address(are, i + "로", i + "detail주소");
            addressRepository.save(address);

            Member find = memberRepository.findById(3L).orElseThrow();

//            Store store = new Store(find, address, i + "store",
//                    1000L, null, null, category, null, null, null);
            //storeRepository.save(store);


        }
    }

    @Test
    @DisplayName("상품 생성")
//    @Transactional
    public void saveProduct() {

//        Store store = storeRepository.findById(UUID.fromString("10c84f88-1b97-40de-9fba-a7c59de48f20")).orElseThrow();
//
//
//        for (int i = 10; i < 20; i++) {
//            Product product = new Product(store, null, "asd", i+"상품", i, null);
//
//        }


    }



    @Test
    @DisplayName("주문 생성")
    @Transactional
    public void save(){
        Member member = new Member("membe1","asd","asd@email.com","asd", null, true);
        memberRepository.save(member);



        Order order = Order.builder()
                .order_type(OrderType.ONLINE)
                .price(20000L)
                .build();

        repository.save(order);

//        Assertions.assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("주문 삭제")
    @Transactional
    public void delete(){

        Order order = Order.builder()
                .order_type(OrderType.ONLINE)
                .price(1000L)
                .build();
        repository.save(order);
        repository.delete(order);
        Assertions.assertThat(repository.findAll()).hasSize(1);

    }

    @Test
    @DisplayName("주문 수정")
//    @Transactional
    public void update(){
        Order order = Order.builder()
                .order_type(OrderType.ONLINE)
                .price(1000L)
                .build();
        repository.save(order);

        Order find = repository.findById(order.getOrder_id()).orElseThrow(NullPointerException::new);

//        find.changePrice(2000L);
        repository.save(find);

        Order update = repository.findById(find.getOrder_id()).orElseThrow(NullPointerException::new);
        Assertions.assertThat(update.getPrice()).isEqualTo(2000L);


    }




}