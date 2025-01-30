using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class CustomerSubscriptionsJunction
    {
        public int CustomerPlanId { get; set; }
        public int Uid { get; set; }
        public int PlanId { get; set; }

        public virtual CustomerSubscribedPlan CustomerPlan { get; set; } = null!;
        public virtual VendorSubscriptionPlan Plan { get; set; } = null!;
        public virtual User UidNavigation { get; set; } = null!;
    }
}
