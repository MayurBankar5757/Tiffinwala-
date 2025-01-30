using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class VendorSubscriptionPlan
    {
        public VendorSubscriptionPlan()
        {
            CustomerSubscribedPlans = new HashSet<CustomerSubscribedPlan>();
            CustomerSubscriptionsJunctions = new HashSet<CustomerSubscriptionsJunction>();
            Feedbacks = new HashSet<Feedback>();
            Tiffins = new HashSet<Tiffin>();
        }

        public int PlanId { get; set; }
        public int VendorId { get; set; }
        public string Name { get; set; } = null!;
        public int Price { get; set; }
        public string Description { get; set; } = null!;
        public byte[]? Image { get; set; }
        public bool? IsAvailable { get; set; }
        public string Duration { get; set; } = null!;

        public virtual Vendor Vendor { get; set; } = null!;
        public virtual ICollection<CustomerSubscribedPlan> CustomerSubscribedPlans { get; set; }
        public virtual ICollection<CustomerSubscriptionsJunction> CustomerSubscriptionsJunctions { get; set; }
        public virtual ICollection<Feedback> Feedbacks { get; set; }
        public virtual ICollection<Tiffin> Tiffins { get; set; }
    }
}
