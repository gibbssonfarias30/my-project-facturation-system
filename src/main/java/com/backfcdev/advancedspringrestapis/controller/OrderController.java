package com.backfcdev.advancedspringrestapis.controller;

import com.backfcdev.advancedspringrestapis.dto.CategoryDTO;
import com.backfcdev.advancedspringrestapis.dto.OrderDTO;
import com.backfcdev.advancedspringrestapis.model.Order;
import com.backfcdev.advancedspringrestapis.service.IOrderService;
import com.backfcdev.advancedspringrestapis.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final IOrderService orderService;
    private final ModelMapper mapper;
    private final PaginationLinksUtils paginationLinksUtils;

    @GetMapping
    ResponseEntity<Page<OrderDTO>> findAll(@PageableDefault() Pageable pageable, HttpServletRequest request){
        Page<Order> ordersPage = orderService.findAll(pageable);
        Page<OrderDTO> orders = ordersPage.map(this::convertToDto);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        return ResponseEntity.ok().header("link",
                        paginationLinksUtils.createLinkHeader(orders, uriBuilder))
                .body(orders);
    }

    @PostMapping
    ResponseEntity<OrderDTO> save(@RequestBody OrderDTO orderDTO){
        Order order = convertToEntity(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(convertToDto(orderService.save(order)));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(convertToDto(orderService.findById(id)));
    }

    // Hateoas - Nivel 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<OrderDTO> findByIdHateoas(@PathVariable Integer id){
        EntityModel<OrderDTO> resource = EntityModel.of(this.convertToDto(orderService.findById(id)));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("order-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderDTO> update(@PathVariable Integer id, @RequestBody OrderDTO orderDTO){
        Order order = orderService.update(id, convertToEntity(orderDTO));
        return ResponseEntity.ok(convertToDto(order));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id){
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public OrderDTO convertToDto(Order dto){
        return mapper.map(dto, OrderDTO.class);
    }
    public Order convertToEntity(OrderDTO entity){
        return mapper.map(entity, Order.class);
    }
}
