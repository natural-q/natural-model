package space.qmen.lot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.qmen.lot.dao.OrderDao;
import space.qmen.lot.dao.SpaceDao;
import space.qmen.lot.model.dto.OwnerOrderDTO;
import space.qmen.lot.model.dto.SpaceWeekRuleDTO;
import space.qmen.lot.model.entity.Order;
import space.qmen.lot.model.param.OrderParam;
import space.qmen.lot.model.param.OrderRentingParam;
import space.qmen.lot.model.vo.OrderHistoryOrderVO;
import space.qmen.lot.service.IOrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SpaceDao spaceDao;

    @Override
    public List<Order> listOrder(){
        return orderDao.listOrder();
    }

    @Override
    public Order getOrderById(Long id) { return orderDao.getOrderById(id); }

    @Override
    public Long deleteOrder(Long id) {
        return orderDao.deleteOrder(id);
    }

    @Override
    public Long saveOrder(OrderRentingParam orderRentingParam) {
        int periodType = orderRentingParam.getPeriodType();

        // 长租,则添加规则号
        if (periodType == 3) {
            SpaceWeekRuleDTO spaceRule = spaceDao.getSpaceRuleBySpaceId(orderRentingParam.getSpaceId());
            orderRentingParam.setRuleId(spaceRule.getRuleId());
        }
        orderDao.saveOrderRentingStatus(orderRentingParam);
        return orderDao.saveOrderFirstly();
    }

    @Override
    public Long updateOrder(Order order) { return orderDao.updateOrder(order); }

    @Override
    public List<OwnerOrderDTO> listOrderByOwnerId(OrderParam orderParam){
        return orderDao.listOrderByOwnerId(orderParam);
    }

    @Override
    public List<OrderHistoryOrderVO> listSpaceHistoryOrder(Long spaceId) {
        return orderDao.listSpaceHistoryOrder(spaceId);
    };
}
