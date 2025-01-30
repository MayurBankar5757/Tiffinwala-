using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class Tiffin
    {
        public int TiffinId { get; set; }
        public int VSubscriptionId { get; set; }
        public string TiffinName { get; set; } = null!;
        public string Day { get; set; } = null!;
        public string Description { get; set; } = null!;
        public string FoodType { get; set; } = null!;

        public virtual VendorSubscriptionPlan VSubscription { get; set; } = null!;
    }
}
